// Small progressive enhancements for the Smart Voting System demo.
document.addEventListener("DOMContentLoaded", () => {
  document.querySelectorAll("form.vote-form").forEach(form => {
    form.addEventListener("submit", event => {
      if (!form.querySelector('input[name="candidateId"]:checked')) {
        event.preventDefault();
        alert("Please select a candidate before continuing.");
      }
    });
  });
});
