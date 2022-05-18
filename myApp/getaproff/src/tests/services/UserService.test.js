import {userService} from "../../services";
import axios from "axios";
import {user1, user2, user3} from "../mock";

jest.mock('axios');

describe("Fetching users", () => {
    test("It should return user with id = 1", async () => {
        const response = {
            status: 200,
            data: user1
        }
        axios.get.mockImplementationOnce(() => Promise.resolve(response));
        userService.getUserInfo(1)
            .then(response => {
                expect(response.failed).toBeFalsy();
                expect(response.data).toBe(user1);
            });
    })

    test("It should return a list with all the users", async () => {
        const response = {
            status: 200,
            data: [user1, user2, user3]
        }
        axios.get.mockImplementationOnce(() => Promise.resolve(response));
        userService.getUserInfo(1)
            .then(response => {
                expect(response.failed).toBeFalsy();
                expect(response.data.length).toBe(3);
                expect(response.data[1]).toBe(user2)
            });
    })
})

describe("Creating Users", () => {
    test("It should create a new user as teacher", async () => {
        const response = {
            status: 201,
            data: [user1, user2, user3]
        }
        const formData = {
            role: 1
        }
        axios.post.mockImplementationOnce(() => Promise.resolve(response));
        userService.register(formData)
            .then(response => console.log(response))
    })
})