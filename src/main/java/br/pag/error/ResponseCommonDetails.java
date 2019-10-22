package br.pag.error;

/**
 *
 * @author brunner.klueger
 */
public class ResponseCommonDetails extends ErrorDetails {

    public ResponseCommonDetails() {
    }

    public static final class Builder {

        private String title;
        private int status;
        private String detail;
        private long timeStamp;
        private String developerMessage;

        public Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public Builder timeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        public Builder developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public ResponseCommonDetails build() {
            ResponseCommonDetails responseDetails = new ResponseCommonDetails();
            responseDetails.developerMessage = this.developerMessage;
            responseDetails.title = this.title;
            responseDetails.detail = this.detail;
            responseDetails.timeStamp = this.timeStamp;
            responseDetails.status = this.status;
            return responseDetails;
        }

    }
}
