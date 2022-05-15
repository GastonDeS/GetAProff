const mockLocalStorage = (function () {
  let store = {};

  return {
    getItem: function (key) {
      return store[key] || null;
    },
    setItem: function (key, value) {
      store[key] = value.toString();
    },
    removeItem: function (key) {
      delete store[key];
    },
    clear: function () {
      store = {};
    }
  };
})();

describe('storage', () => {
  beforeAll(() => {
    Object.defineProperty(window, 'localStorage', {
      value: mockLocalStorage,
    });
  });
});