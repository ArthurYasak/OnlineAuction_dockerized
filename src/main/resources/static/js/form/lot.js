let inputs = document.querySelectorAll('input');
let buttonSend = document.getElementById('submit');

let inputLengthValidator = {
    "name": document.getElementById('name').value.length > 0,
    "type": document.getElementById('type').value.length > 0,
    "weight": document.getElementById('weight').value.length > 0,
    "size": document.getElementById('size').value.length > 0,
    "sellUntil": document.getElementById('sellUntil').value.length > 0,
    "minPrice": document.getElementById('minPrice').value.length > 0
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