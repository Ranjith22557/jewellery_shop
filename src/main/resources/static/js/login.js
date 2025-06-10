const firebaseConfig = {
  apiKey: "AIzaSyDMAxs0gwX6D4ijN_H72QxE-lDS-1W2BgY",
  authDomain: "jewellery-a8180.firebaseapp.com",
  projectId: "jewellery-a8180",
  storageBucket: "jewellery-a8180.firebasestorage.app",
  messagingSenderId: "102826613849",
  appId: "1:102826613849:web:93b36e0bfaada50f98e548",
  measurementId: "G-5JYWZDY16Z"
};

firebase.initializeApp(firebaseConfig);
firebase.analytics();

document.getElementById("firebaseLoginForm").addEventListener("submit", async function (e) {
  e.preventDefault();

  const errorDiv = document.getElementById("error");
  errorDiv.textContent = "";

  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;

  try {
    const userCredential = await firebase.auth().signInWithEmailAndPassword(email, password);
    const idToken = await userCredential.user.getIdToken();

    const res = await fetch("/api/authenticate", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ idToken }),
    });

    if (!res.ok) {
      try {
        const errorData = await res.json();
        console.log("Backend error JSON:", errorData);

        // Extract error message from nested structure
        let backendMessage = "Login failed on server.";
        if (errorData.message) {
          backendMessage = errorData.message;
        } else if (errorData.error && errorData.error.message) {
          backendMessage = errorData.error.message;
        }

        errorDiv.textContent = "Authentication error: " + backendMessage;
      } catch (parseError) {
        console.error("Failed to parse error JSON:", parseError);
        const text = await res.text();
        console.log("Backend error text:", text);
        errorDiv.textContent = "Authentication error: " + text;
      }
    }else{
        window.location.href = "/home";
    }


  } catch (error) {
    if (error.code) {
      switch (error.code) {
        case 'auth/user-not-found':
          errorDiv.textContent = "User not found. Please register.";
          break;
        case 'auth/wrong-password':
          errorDiv.textContent = "Incorrect password.";
          break;
        case 'auth/invalid-email':
          errorDiv.textContent = "Invalid email format.";
          break;
        case 'auth/internal-error':
          errorDiv.textContent = "User not found. Please register.";
          break;
        default:
          errorDiv.textContent = "Authentication error: " + error.message;
      }
    } else {
      errorDiv.textContent = "Unexpected error: " + error.message;
    }
  }
});
