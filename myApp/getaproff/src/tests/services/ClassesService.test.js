import axios from "axios";
import {classesService} from "../../services";

jest.mock("axios")

describe("Fetch classes", () => {
    test("It should return a list of the user current classes", () => {
        classesService.getUserClasses(true, 1)
    })
})