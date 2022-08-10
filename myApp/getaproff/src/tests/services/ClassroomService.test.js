import {classroomService} from "../../services";
import axios from "axios";
import {
    classroom1,
    classroom1files,
    post1,
    post2,
    subjectFile1,
    successfulResponseMock
} from "../mock";

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
    });

    test("It should return a list of classroom posts", () => {
        const data = [post1, post2];
        successfulResponseMock(200,{}, data)
        classroomService.fetchClassroomPosts(1,1)
            .then(response => {
                expect(response.failure).toBeFalsy();
                expect(response.status).toBe(200)
                expect(response.data).toBe(data);
                expect(axios.get).toHaveBeenCalledTimes(1);
            })
    })

})

describe("Modifying classroom", () => {
    test("It should accept the pending class", async () => {
        const response = {
            status: 204
        }
        axios.post.mockImplementationOnce(() => Promise.resolve(response));
        classroomService.acceptClass(1)
            .then(res => {
                expect(res.status).toBe(204);
                expect(axios.post).toHaveBeenCalledTimes(1);
                expect(res.failure).toBeFalsy()
            })

    });
    test("It should finish the accepted class", async () => {
        const response = {
            status: 204
        }
        axios.post.mockImplementationOnce(() => Promise.resolve(response));
        classroomService.finishClass(1)
            .then(res => {
                expect(res.status).toBe(204);
                expect(axios.post).toHaveBeenCalledTimes(1);
                expect(res.failure).toBeFalsy()
            })
    });

    test("It should share a subject file", async () => {
        const response = { status: 200}
        axios.post.mockImplementationOnce(() => Promise.resolve(response));
        classroomService.startSharingFile([subjectFile1.id], classroom1.classId)
            .then(response => {
                expect(response.failure).toBeFalsy();
                expect(response.status).toBe(200);
                expect(axios.post).toHaveBeenCalledTimes(1);
            })
    });

    test("It should stop sharing a subject file", async () => {
        const response = { status: 200};
        axios.delete.mockImplementationOnce(() => Promise.resolve(response));
        classroomService.stopSharingFile(subjectFile1.id, classroom1.classId)
            .then(response => {
                expect(response.failure).toBeFalsy();
                expect(response.status).toBe(200);
                expect(axios.delete).toHaveBeenCalledTimes(1);
            })
    });

    test("It should rate a class", async () => {
        const response = {status: 200};
        axios.post.mockImplementationOnce(() => Promise.resolve(response));
        classroomService.rateClass(classroom1.classId)
            .then(response => {
                expect(axios.post).toHaveBeenCalledTimes(1);
                expect(response.failure).toBeFalsy();
                expect(response.status).toBe(200);
            })
    })
});