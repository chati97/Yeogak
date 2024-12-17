export default function deepCopy(origin) {
  let result = {};

  for (let key in origin) {
    if (typeof origin[key] === "object") {
      result[key] = deepCopy(origin[key]);
    } else {
      result[key] = origin[key];
    }
  }
  return result;
}
