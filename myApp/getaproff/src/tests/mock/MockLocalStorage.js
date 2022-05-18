export class LocalStorageMock {
  store;
  length = 1;
  constructor() {
    this.store = {};
  }

  clear() {
    this.store = {};
  }
  key(index) {
    return this.store[index];
  }

  getItem(key) {
    return this.store[key];
  }

  setItem(key, value) {
    this.store[key] = String(value);
  }

  removeItem(key) {
    delete this.store[key];
  }
}

global.localStorage = new LocalStorageMock();
test("", () => {});

// describe('storage', () => {
//   beforeAll(() => {
//     Object.defineProperty(window, 'localStorage', {
//       value: localStorage,
//     });
//   });
// });

