const apiBase = "http://localhost:8080";

function show(id, obj) {
    document.getElementById(id).textContent =
        typeof obj === "string" ? obj : JSON.stringify(obj, null, 2);
}

function saveJwt(token) {
    localStorage.setItem("jwt", token);
}

function getJwt() {
    return localStorage.getItem("jwt");
}
