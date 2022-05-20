import axios from "axios";
import {ratingService} from "../../services";
import {review1, review2, successfulResponseMock} from "../mock";
jest.mock("axios")

describe("Fetching user reviews" ,() => {
    test("It should return the reviews of user", async () => {
        const data = [review1, review2];
        successfulResponseMock(200,{}, data)
        ratingService.getUserReviews(1, 1)
            .then(response => {
                expect(response.failure).toBeFalsy();
                expect(response.data.length).toBe(2)
                expect(axios.get).toHaveBeenCalledTimes(1);
            })
    });

    test("", async () => {

    })
})