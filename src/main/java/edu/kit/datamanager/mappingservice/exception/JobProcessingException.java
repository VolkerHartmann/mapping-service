/*
 * Copyright 2024 Karlsruhe Institute of Technology.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.kit.datamanager.mappingservice.exception;

/**
 *
 * @author jejkal
 */
public class JobProcessingException extends Exception {

    private boolean badRequest = false;

    public JobProcessingException() {
        super();
    }

    public JobProcessingException(String message) {
        super(message);
    }

    public JobProcessingException(String message, boolean badRequest) {
        super(message);
        this.badRequest = badRequest;
    }

    public boolean isBadRequest() {
        return badRequest;
    }

}
