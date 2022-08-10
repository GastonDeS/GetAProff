import {subjectService} from "../../services"
import axios from "axios";
import {subject1, subject2, successfulResponseMock} from "../mock";

jest.mock("axios")

describe("Fetching subjects", () => {
        test("It should return a list with the most requested subjects", async () => {
            const data = [subject1, subject2];
            successfulResponseMock(200, {}, data);
            subjectService.getMostRequestedSubjects()
                .then(response => {
                    expect(response.failure).toBeFalsy();
                    expect(response.data.length).toBe(2)
                })
        })
    }
)

describe("Posting subject request", () => {
    test("It should return a status code 200", async () => {
        const response = {status: 200}
        const postData = {
            message: "I'd really like to teach this subject",
            subject: "Astrophysics"
        }
        axios.post.mockImplementationOnce(() => Promise.resolve(response))
        subjectService.requestSubject(postData)
            .then(response => {
                expect(response.failure).toBeFalsy();
                expect(response.status).toBe(200);
            })
    })
})