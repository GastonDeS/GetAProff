import axios from "axios";
import {classesService} from "../../services";
import {class1, class2, successfulResponseMock, userSubject1} from "../mock";

jest.mock("axios")

describe("Fetch classes", () => {
    test("It should return a list of the user current classes with status = 1", () => {
        const data = [class1, class2]
        successfulResponseMock(200, {}, data)
        classesService.getUserClasses(true, 1)
            .then(response => {
                expect(response.status).toBe(200);
                expect(response.failure).toBeFalsy();
                expect(response.data.length).toBe(2);
                expect(axios.get).toHaveBeenCalledTimes(1);
            })
    });
});

describe("Request class", () => {
    test("It should return status 201 created ", async () => {
        const response = {status : 201};
        const postData = {
            teacherId: 1,
            subject: userSubject1,
            level: 2,
            price: 1000,
        }
        axios.post.mockImplementationOnce(() => Promise.resolve(response));
        classesService.requestClass(postData)
            .then(response => {
                expect(response.failure).toBeFalsy();
                expect(response.status).toBe(201);
                expect(axios.post).toHaveBeenCalledTimes(1);
            })
    })
})