document.addEventListener('DOMContentLoaded', function () {
  var logoutButton = document.querySelector('#logoutButton');
  if (logoutButton) {
    logoutButton.addEventListener('click', function (event) {
      event.preventDefault();

      $.get('/api/logout')
      .done(function (response) {
        alert(response);
        window.location.href = '/';
      })
      .fail(function (error) {
        console.error('로그아웃 오류:', error);
      });
    });
  }
});