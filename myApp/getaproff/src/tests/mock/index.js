import {LocalStorageMock} from "./MockLocalStorage";
import axios from "axios";

export const user1 = {
    description: "I\'m John",
    id: 1,
    mail: "user1@mail.com",
    maxPrice: 1000,
    minPrice: 100,
    name: "John",
    rate: 4.0,
    reviewsQty: 2,
    schedule: "Mon-Fri 10-12",

}
export const user2 = {
    description: "I\'m Peter",
    id: 2,
    mail: "user2@mail.com",
    maxPrice: 2200,
    minPrice: 200,
    name: "Peter",
    rate: 2.4,
    reviewsQty: 2,
    schedule: "Mon-Thu 8-12",

}
export const user3 = {
    description: "I\'m Ana",
    id: 3,
    mail: "user3@mail.com",
    maxPrice: 3000,
    minPrice: 130,
    name: "Ana",
    rate: 3.0,
    reviewsQty: 1,
    schedule: "Tue-Thu 10-22",

}

//Classes mocks

export const class1 = {
    classId: 2,
    files: {
        rel: 2,
        href: "http://localhost:8080/api/post/2/files"
    },
    level: 0,
    notifications: 0,
    posts: {
        rel: 2,
        href: "http://localhost:8080/api/classroom/73/posts"
    },
    price: 1000,
    status: 1,
    student: user1,
    subjectName: "Math I",
    teacher: user3
}

export const class2 = {
    classId: 3,
    files: {
        rel: 3,
        href: "http://localhost:8080/api/post/3/files"
    },
    level: 1,
    notifications: 0,
    posts: {
        rel: 3,
        href: "http://localhost:8080/api/classroom/3/posts"
    },
    price: 1000,
    status: 1,
    student: user2,
    subjectName: "Math I",
    teacher: user3
}

//Classroom Mocks

export const classroom1 = {
    classId: 1,
    files: {
        rel: 1,
        href: "http://localhost:8080/api/classroom/1/files"
    },
    level: 1,
    notifications: 0,
    posts: {
        rel: 1,
        href: "http://localhost:8080/api/classroom/1/posts"
    },
    price: 2000,
    status: 1,
    student: user1,
    subjectName: "Mate 1",
    teacher: user2
}

export const pendingClassroom = {
    classId: 2,
    files: {
        rel: 2,
        href: "http://localhost:8080/api/classroom/2/files"
    },
    level: 1,
    notifications: 0,
    posts: {
        rel: 2,
        href: "http://localhost:8080/api/classroom/2/posts"
    },
    price: 2000,
    status: 0,
    student: user1,
    subjectName: "Mate 2",
    teacher: user2
}

export const classroom1files = {
    notShared: [
        {
            rel: 1,
            href: "http://localhost:8080/api/subject-files/1",
            title: "file1.pdf"
        }
    ],
    shared: [
        {
            rel: 2,
            href: "http://localhost:8080/api/subject-files/2",
            title: "file2.pdf"
        },
        {
            rel: 3,
            href: "http://localhost:8080/api/subject-files/3",
            title: "file3.pdf"
        }
    ]
}

//Subjects

export const subject1 = {
    name: "Math",
    subjectId: 1,
}
export const subject2 = {
    name: "Math II",
    subjectId: 2,
}
export const subject3 = {
    name: "Math III",
    subjectId: 3,
}

//Reviews

export const review1 = {
    rate: 5.0,
    review: "Great class",
    student: "Matt",
    teacherId: 1
}
export const review2 = {
    rate: 3.0,
    review: "I hope he can be more polite in the next class",
    student: "Aaron",
    teacherId: 1
}

//Subject Files

export const subjectFile1 = {
    id: 1,
    level: 3,
    name: "FirstFile.pdf",
    subject: {
        name: "Math II",
        subjectId: 2,
        url: {
            rel: "2",
            href: "http://localhost:8080/api/subjects/2"
        }
    },
    url: {
        rel: "1",
        href: "http://localhost:8080/api/subject-files/1"
    }
}

export const subjectFile2 = {
    id: 2,
    level: 2,
    name: "SecondFile.pdf",
    subject: {
        name: "Math I",
        subjectId: 1,
        url: {
            rel: "1",
            href: "http://localhost:8080/api/subjects/1"
        }
    },
    url: {
        rel: "2",
        href: "http://localhost:8080/api/subject-files/2"
    }
}

//User subjects

export const userSubject1 = {
    id: 1,
    levels: [
        1,
        0
    ],
    prices: [
        2000,
        900
    ],
    subject: "Math"
}

export const userSubject2 = {
    id: 2,
    levels: [
        2,
        1
    ],
    prices: [
        3000,
        1900
    ],
    subject: "Math II"
}


//Classroom posts
export const post1 = {
    id: 1,
    message: "Keen on learning about maths!",
    time: "2022-03-19T23:25:40.749-03:00",
    uploader: user1.name
}

export const post2 = {
    id: 2,
    message: "Hello student",
    time: "2022-03-20T23:25:40.749-03:00",
    uploader: user2.name
}

export const successfulResponseMock = (httpCode, headers, body = {}) => {
    axios.get.mockImplementationOnce(() => {
        return new Promise((resolve, reject) => {
            resolve({
                ok: true,
                status: httpCode,
                headers: headers,
                data: body
            });
        });
    });
}