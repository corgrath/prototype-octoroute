package octoroute.network;

import octoroute.action.RequestAction;
import octoroute.exceptions.OctorouteException;
import octoroute.services.LogServiceInterface;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class NetworkService implements NetworkServiceInterface {

    private static final int STATUS_200_OK = 200;
    private static final int STATUS_202_ACCEPTED = 202;
    private static final int STATUS_204_NO_CONTENT = 204;

    private final LogServiceInterface logService;

    public NetworkService(LogServiceInterface logService) {
        this.logService = logService;
    }

    @Override
    public void execute(RequestAction request) throws OctorouteException {

        String url = request.getURL();
        Map<String, String> headers = request.getHeaders();
        String postData = request.requestData();

        if (request.getMethod() == HTTPRequestMethod.POST) {
            this.post(url, headers, postData);
        } else {
            throw new OctorouteException("Unknown method " + request.getMethod());
        }

    }

    public Response post(String url, Map<String, String> headers, String postData) throws OctorouteException {
        HttpPost request = new HttpPost(url);

        if (postData != null) {
            request.setEntity(new StringEntity(postData, StandardCharsets.UTF_8));
        }

        try {
            return execute(request, headers);
        } catch (OctorouteException exception) {
            exception.addMetadata("postData", postData);
            throw exception;
        }
    }

    private Response execute(HttpRequestBase request, Map<String, String> headers) throws OctorouteException {
        try {

            this.logService.log(NetworkService.class.getSimpleName(), "Sending \"" + request.getMethod() + "\" request to \"" + request.getURI().toString() + "\".");

            if (headers != null) {
                appendHeaders(request, headers);
            }

            HttpResponse httpResponseDoNotUse = HttpClientBuilder.create().build().execute(request);

            Response response = new Response(httpResponseDoNotUse);

            List<Integer> successCodes = List.of(
                    NetworkService.STATUS_200_OK,
                    NetworkService.STATUS_202_ACCEPTED,
                    NetworkService.STATUS_204_NO_CONTENT
            );

            String requestURI = request.getURI().toString();
            if (!successCodes.contains(response.getStatusCode())) {
                System.out.println(response);
                String responseBody = response.getBodyAsString();
                System.out.println(responseBody);
                int statusCode = response.getStatusCode();
                StatusLine statusLine = response.getStatusLine();
//                StatusLine statusLine = httpResponse.getStatusLine();
                throw new OctorouteException("Request to " + requestURI + " failed with with status " + statusCode + ":" + statusLine + ":" + responseBody, Map.of(
                        "status", Integer.toString(response.getStatusCode())
                ));
            }

            return response;

        } catch (IOException exception) {
            exception.printStackTrace();
            throw new OctorouteException(exception);
        }

    }

    private void appendHeaders(HttpUriRequest request, Map<String, String> headers) {

        request.setHeader("User-Agent", "octoroute");

        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                request.setHeader(entry.getKey(), entry.getValue());
            }
        }

    }

    public static class Response {

        private final HttpResponse response;
        private final Header[] headers;
        private final StatusLine statusLine;

        public Response(HttpResponse response) {
            this.response = response;
            this.headers = this.response.getAllHeaders();
            this.statusLine = response.getStatusLine();
        }

        public int getStatusCode() {
            return this.response.getStatusLine().getStatusCode();
        }

        public String getBodyAsString() throws OctorouteException {
            try {
                HttpEntity entity = this.response.getEntity();
                if (entity == null) {
                    return "";
                }
                return IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8);
            } catch (IOException exception) {
                throw new OctorouteException(exception);
            }
        }

        public byte[] getBodyAsByte() throws OctorouteException {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                this.response.getEntity().writeTo(baos);
                return baos.toByteArray();
            } catch (Exception exception) {
                throw new OctorouteException(exception);
            }
        }

        public Header[] getHeaders() {
            return this.headers;
        }

        public StatusLine getStatusLine() {
            return this.statusLine;
        }
    }

}