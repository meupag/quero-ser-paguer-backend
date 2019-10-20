package br.pag.error;

import java.util.Map;
import lombok.Getter;

/**
 *
 * @author brunner.klueger
 */
@Getter
public class ResponseBeanValidationDetails extends ErrorDetails {

    private Map<String, String> fieldErrorMessage;

    public static final class Builder {

        private String title;
        private int status;
        private String detail;
        private long timeStamp;
        private String developerMessage;
        private Map<String, String> fieldErrorMessage;

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

        public Builder fieldErrorMessage(Map<String, String> fieldErrorMessage) {
            this.fieldErrorMessage = fieldErrorMessage;
            return this;
        }

        public ResponseBeanValidationDetails build() {
            ResponseBeanValidationDetails responseDetails = new ResponseBeanValidationDetails();
            responseDetails.developerMessage = this.developerMessage;
            responseDetails.title = this.title;
            responseDetails.detail = this.detail;
            responseDetails.timeStamp = this.timeStamp;
            responseDetails.status = this.status;
            responseDetails.fieldErrorMessage = this.fieldErrorMessage;
            return responseDetails;
        }

    }
}
