let inputs = document.querySelectorAll('input');
let buttonSend = document.getElementById('submit');

let inputValidator = {
  "betPrice": true
}

inputs.forEach((input) => {
  input.addEventListener('input', () => {
    let name = event.target.getAttribute('name');
    if (Object.keys(inputValidator).includes(name)) {
        if (event.target.value.length > 0) {
          inputValidator[name] = true;
        } else {
          inputValidator[name] = false;
        };
    }

    let allTrue = Object.keys(inputValidator).every((item) => {
      return inputValidator[item] === true
    });

    if (allTrue) {
      buttonSend.disabled = false;
    } else {
      buttonSend.disabled = true;
    }
  })
})