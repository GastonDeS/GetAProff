import React from 'react';
import '@testing-library/jest-dom/extend-expect';
import { render} from '@testing-library/react';
import TutorCard from "../components/TutorCard";

const mockedUsedNavigate = jest.fn();

jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => mockedUsedNavigate,
}));

test("TutorCard renders content", () => {
    const user = {
        name: 'John',
        description: 'Brief intro about me',
        minPrice: 100,
        maxPrice: 1000,

    }
    const component = render(<TutorCard user={user}/>)
    expect(component.container).toHaveTextContent(user.name);
    expect(component.container).toHaveTextContent(user.description)
});