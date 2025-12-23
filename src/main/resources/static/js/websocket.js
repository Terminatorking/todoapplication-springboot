document.addEventListener("DOMContentLoaded", function () {
  const userInfo = document.getElementById("user-info");
  if (!userInfo) {
    console.error("User info element not found.");
    return;
  }

  const userId = userInfo.getAttribute("data-user-id");

  if (userId) {
    const socket = new SockJS("/ws");
    const stompClient = Stomp.over(socket);

    stompClient.connect(
      {},
      function (frame) {
        console.log("Connected: " + frame);
        const subscriptionUrl = "/topic/reminders/" + userId;
        console.log("Subscribing to: " + subscriptionUrl);

        stompClient.subscribe(subscriptionUrl, function (reminder) {
          // Show a simple browser alert when a reminder is received
          alert(reminder.body);
        });
      },
      function (error) {
        console.error("STOMP error: " + error);
      }
    );
  } else {
    console.error("User ID not found.");
  }
});
