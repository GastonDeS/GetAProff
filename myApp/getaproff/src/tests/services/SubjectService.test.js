import {classroomService, subjectService} from "../../services"
import axios from "axios";
import {subject1, subject2} from "../mock";

jest.mock("axios")

describe("Fetching subjects", () => {
        test("It returns a list with the most requested subejcts", async () => {
            const response = {
                status: 200,
                data : [subject1, subject2]
            }
            axios.get.mockImplementationOnce(() => Promise.resolve(response))
            subjectService.getMostRequestedSubjects()
                .then(response => {
                    expect(response.failure).toBeFalsy();
                    expect(response.data.length).toBe(2)
                })
        })
    }
)