jQuery(document).ready(function($) {
    let inputs = document.querySelectorAll('input');
    let buttonSend = document.getElementById('submit');

    let inputValidator = {
      "username": document.getElementById('username').value.length > 0,
      "password": document.getElementById('password').value.length > 0
    }

    inputs.forEach((input) => {
      input.addEventListener('input', () => {
        let name = event.target.getAttribute('name');
        if (event.target.value.length > 0) {
          inputValidator[name] = true;
        } else {
          inputValidator[name] = false;
        }

        let allTrue = Object.keys(inputValidator).every((item) => {
          return inputValidator[item] === true
        });

        if (allTrue) {
          buttonSend.disabled = false;
        } else {
          buttonSend.disabled = true;
        }

      });

    });
});
