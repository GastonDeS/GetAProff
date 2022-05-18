export const APPLICATION_V1_JSON_TYPE = 'application/vnd.getaproff.api.v1+json'

export const API_PREFIX = '/api'

export const paths = {
  USERS: 'users',
  FAVOURITES: 'favourites',
  RATINGS: 'ratings',
  CLASSES: 'classes',
  CLASSROOM: 'classroom',
  USER_FILES: 'user-files',
  SUBJECT_FILES: 'subject-files',
  SUBJECTS: 'subjects',
  POST: 'post'
}

export const status = {
  OK: 200,
  SUCCESS_LIMIT: 299,
  NO_CONTENT: 204,
  UNAUTHORIZED: 401,
  PAGE_NOT_FOUND: 404
}

export const classStatus = {
  ANY: -1,
  PENDING: 0,
  ACCEPTED: 1,
  FINISHED: 2,
  CANCELLEDS: 3,
  CANCELLEDT: 4,
  REJECTED: 5,
  RATED: 6,
  ALLCANCELLED: 7
}