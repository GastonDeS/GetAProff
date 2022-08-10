import {userService} from "../../services";
import axios from "axios";
import {subject3, successfulResponseMock, user1, user2, user3, userSubject1, userSubject2} from "../mock";

jest.mock('axios');

describe("Fetching users info and related", () => {
    test("It should return user with id = 1", async () => {
        successfulResponseMock(200,{} , user1 )
        userService.getUserInfo(1)
            .then(response => {
                console.log(response)
                expect(response.failed).toBeFalsy();
                expect(response.data).toBe(user1);
                expect(axios.get).toBeCalledTimes(1)
            });
    });

    test("It should return a list with all the users", async () => {
        const data = [user1, user2, user3];
        const queryParams = {
            maxPrice: 100,
            level: 1,
            search: ' ',
            order: 1,
            rating: 0
        }
        successfulResponseMock(200, {} , data)
        userService.getUsers(queryParams, 1)
            .then(response => {
                console.log(response)
                // expect(response.failed).toBeFalsy();
                // expect(axios.get).toBeCalledTimes(1)
                // expect(response.data.length).toBe(3);
                // expect(response.data[1]).toBe(user2)
            });
    });

    test("It should return a list with all the user subjects", async () => {
        const data = [userSubject1, userSubject2];

        successfulResponseMock(200, {}, data);

        userService.getUserSubjects(1)
            .then(response => {
                expect(response.status).toBe(200);
                expect(response.data.length).toBe(2);
                expect(response.failure).toBeFalsy();
                expect(axios.get).toBeCalledTimes(1)
            })
    });

    test("It should fetch the most requested and best rated users", async () => {
        const data = {
            mostRequested: [user1, user2],
            bestRated: [user1]
        }
        successfulResponseMock(200,{}, data)
        userService.getHomeTeachers()
            .then(response => {
                expect(response.status).toBe(200);
                expect(response.data.mostRequested.length).toBe(2);
                expect(response.failure).toBeFalsy();
                expect(axios.get).toHaveBeenCalledTimes(1)
            })
        }
    );

    test("It should return a list of subjects that the user can start teaching", async () => {
        const data = [subject3]
        successfulResponseMock(200,{}, data);
        userService.getUserAvailableSubjects(user1.id)
            .then(
                response => {
                    expect(response.failure).toBeFalsy();
                    expect(response.data[0]).toBe(subject3);
                    expect(axios.get).toHaveBeenCalledTimes(1);
                    expect(response.status).toBe(200)
                }
            )
    })
});

describe("Posting Users & data", () => {
    test("It should create a new user as student", async () => {
        const response = {
            status: 201,
            data: {
                id: 1,
                mail:"james@mail.com",
                name:"James",
                teacher:false,
                url: {
                    rel:"1",
                    href:"http://localhost:8080/api/users/1"
                }
            }
        }

        const formData = {
            mail:"james@mail.com",
            name:"James",
            teacher:false,
        }
        axios.post.mockImplementationOnce(() => Promise.resolve(response))
        userService.register(formData)
            .then(response => {
                expect(response.failure).toBeFalsy();
                expect(axios.post).toHaveBeenCalledTimes(1)
                expect(response.status).toBe(201)
            })
    });

    test("It should add a subject to an user subjects list", async () => {
        const response = {
            status: 201
        }
        axios.post.mockImplementationOnce(() => Promise.resolve(response))
        userService.addSubjectToUser(user1.id, subject3.subjectId, 1000, 1)
            .then(
                response => {
                    expect(response.failure).toBeFalsy();
                    expect(response.status).toBe(201);
                }
            )
    })
})
