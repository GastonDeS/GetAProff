
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
            href: "http://localhost:8080/api/subject-files/17",
            title: "file1.pdf"
        }
    ],
    shared: [
        {
            rel: 2,
            href: "http://localhost:8080/api/subject-files/18",
            title: "file2.pdf"
        },
        {
            rel: 3,
            href: "http://localhost:8080/api/subject-files/12",
            title: "file3.pdf"
        }
    ]
}

export const successfulResponseMock = (httpCode, headers, body={}) => {
    global.localStorage = new LocalStorageMock()
    return (global.fetch = jest.fn().mockImplementationOnce(() => {
        return new Promise((resolve, reject) => {
            resolve({
                ok: true,
                status: httpCode,
                headers: headers,
                json: () => {
                    return body;
                }
            });
        });
    }));
}