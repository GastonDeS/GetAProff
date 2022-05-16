import { status } from "../assets/constants";

export class Response {
  handleResponseStatus (response) {
    
    if (response.status >= status.OK && 
        response.status <= status.SUCCESS_LIMIT &&
        response.status != status.NO_CONTENT) {

        }
  }

  success (response) {
  
  }
}