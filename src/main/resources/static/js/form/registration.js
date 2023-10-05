let inputs = document.querySelectorAll('input');
let buttonSend = document.getElementById('submit');

let inputLengthValidator = {
  "username": document.getElementById('username').value.length > 0,
  "password": document.getElementById('password').value.length > 0,
  "name": document.getElementById('name').value.length > 0,
  "surname": document.getElementById('surname').value.length > 0,
  "age": document.getElementById('age').value.length > 0
}

inputs.forEach((input) => {
  input.addEventListener('input', () => {
    let name = event.target.getAttribute('name');
    if (Object.keys(inputLengthValidator).includes(name)) {
        if (event.target.value.length > 0) {
          inputLengthValidator[name] = true;
        } else {
          inputLengthValidator[name] = false;
        }
    }
    let allTrue = Object.keys(inputLengthValidator).every((item) => {
      return inputLengthValidator[item] === true
    });

    if (allTrue) {
      buttonSend.disabled = false;
    } else {
      buttonSend.disabled = true;
    }
  })
})