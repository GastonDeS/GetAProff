import {successfulResponseMock, user1, user2, user3} from "../mock";
import axios from "axios";
import {favouritesService} from "../../services";

jest.mock("axios")


describe("Fetching user favorites and related info", () => {
    test("It should return a list of user favorites", async () => {
        const data= [user2, user3];
        successfulResponseMock(200, {}, data)
        favouritesService.getFavoriteTeachers()
            .then(response => {
                expect(response.failed).toBeFalsy();
                expect(response.data.length).toBe(2);
            })
    });

    test("It should return status code 200 if teacher is faved", async () => {
        successfulResponseMock(200, {})
        favouritesService.checkIfTeacherIsFaved(user1.id)
            .then(
                response => {
                    console.log(response);
                    expect(response.failure).toBeFalsy();
                    expect(response.status).toBe(200);
                    expect(axios.get).toHaveBeenCalledTimes(1);
                }
            )
    });

    test("It should return status code 204 if teacher is not faved", async () => {
        successfulResponseMock(204, {});
        favouritesService.checkIfTeacherIsFaved(user2.id)
            .then(
                response => {
                    expect(response.failure).toBeFalsy();
                    expect(response.status).toBe(204);
                    expect(axios.get).toHaveBeenCalledTimes(1);
                }
            )
    })

});

describe("Posting data related to user favorites", () => {
    test("It should add teacher to favorites & return status code 200", async () => {
        const response = {status: 200}
        axios.post.mockImplementationOnce(() => Promise.resolve(response))
        favouritesService.addTeacherToFavorites(user1.id)
            .then(
                response => {
                    console.log(response);
                    expect(response.status).toBe(200);
                     expect(response.failure).toBeFalsy();
                    expect(axios.post).toHaveBeenCalledTimes(1);
                }
            )
    });
})