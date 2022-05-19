import {classroomService} from "../../services";
import axios from "axios";
import {classroom1, classroom1files, user1, user2, user3} from "../mock";

jest.mock('axios');

describe("Fetching classroom ", () => {
    test("It should return info of the classroom with id 1", async () => {
        const response = {
            status: 200,
            data: classroom1
        }
        axios.get.mockImplementationOnce(() => Promise.resolve(response));
        classroomService.fetchClassroomInfo(1)
            .then(response => {
                expect(response.failure).toBeFalsy()
                expect(response.data).toBe(classroom1)
            })
    })
    test("It should return the files of the classroom with id 1", async () => {
        const response = {
            status: 200,
            data: classroom1files
        }
        axios.get.mockImplementationOnce(() => Promise.resolve(response));
        classroomService.getClassroomFiles(1)
            .then(response => {
                expect(response.failure).toBeFalsy();
                expect(response.data.shared.length).toBe(2);
                expect(response.data.notShared.length).toBe(1)
            })
    })
})

describe("Modifying classroom", () => {
    test("It should accept the pending class", () => {
        const response = {
            status: 200,
            data: {
                status: 1
            }
        }
        axios.post.mockImplementationOnce(() => Promise.resolve(response))
        classroomService.acceptClass()
    })
    }
)