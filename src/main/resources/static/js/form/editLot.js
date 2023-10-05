let inputs = document.querySelectorAll('input');
let buttonSend = document.getElementById('submit');

let inputLengthValidator = {
  "name": true,
  "type": true,
  "weight": true,
  "size": true,
  "sellUntil": true,
  "minPrice": true
}

inputs.forEach((input) => {
  input.addEventListener('input', () => {
    let name = event.target.getAttribute('name');
    if (Object.keys(inputLengthValidator).includes(name)) {
        if (event.target.value.length > 0) {
          inputLengthValidator[name] = true;
        } else {
          inputLengthValidator[name] = false;
        };
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