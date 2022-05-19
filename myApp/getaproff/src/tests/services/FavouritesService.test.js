import {user1, user2, user3} from "../mock";
import axios from "axios";
import {favouritesService} from "../../services";

jest.mock("axios")


describe("Fetching user favorites", () => {
    test("It should return a list of user favorites", async () => {
        const response = {
            status: 200,
            data: [user2, user3]
        }
        axios.get.mockImplementationOnce(() => Promise.resolve(response));
        favouritesService.getFavoriteTeachers()
            .then(response => {
                expect(response.failed).toBeFalsy();
                expect(response.data.length).toBe(2);
            })
    })
});