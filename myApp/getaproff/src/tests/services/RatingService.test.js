import axios from "axios";
import {ratingService, subjectService} from "../../services";
import {review1, review2} from "../mock";
jest.mock("axios")

describe("Fetching user reviews" ,() => {
    test("It should return the reviews of user", () => {
        const response = {
            status : 200,
            data: [review1, review2]
        }
        axios.get.mockImplementationOnce(() => Promise.resolve(response))
        ratingService.getUserReviews(1, 1)
            .then(response => {
                expect(response.failure).toBeFalsy();
                expect(response.data.length).toBe(2)
            })
    })
    test("It should return  ")
})