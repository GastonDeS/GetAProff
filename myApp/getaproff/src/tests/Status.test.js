import React from 'react'
import '@testing-library/jest-dom/extend-expect'
import { render} from '@testing-library/react'
import Status from "../components/Status";

test('Status renders content', () => {
    const component = render(<Status status={'Accepted'}/>)
    expect(component.container).toHaveTextContent('Accepted');
})