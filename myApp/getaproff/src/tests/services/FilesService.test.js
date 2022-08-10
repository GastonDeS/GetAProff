import axios from "axios"
import {filesService} from "../../services";
import {subjectFile1} from "../mock";

jest.mock("axios")

describe("Fetching files", () => {
    test("It should return the subject file with id = 1", async () => {
        const response = {
            status: 200,
            data: subjectFile1
        }
        axios.get.mockImplementationOnce(() => Promise.resolve(response));
        filesService.getSubjectFile(1)
            .then(response => {
                expect(response.failure).toBeFalsy();
                expect(response.data).toBe(subjectFile1);
            })
    })
})