import React from 'react'
import '@testing-library/jest-dom/extend-expect'
import { render} from '@testing-library/react'
import ClassCard from "../components/ClassCard"

test('renders content', () => {
    const classCard = {
        user: {
            name: 'John'
        },
        level: 1,
        subject: 'Math',
        price: '$1000',
        statusCode: 1,
        isTeacher: false,
        handlers : {
            enterClassroom : () => 'Into classroom',
        }
    }
    const component = render(<ClassCard user={classCard.user} subject={classCard.subject} isTeacher={classCard.isTeacher}
                                        level={classCard.level} price={classCard.price} statusCode={classCard.statusCode}
                                        handlers={classCard.handlers}/>)

    expect(component.container).toHaveTextContent(classCard.subject);


})
