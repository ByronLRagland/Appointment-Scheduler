const API_URL = "http://localhost:8080/api";

function makeUser(body) {
    const sections = body.jwt.split(".");
    const json = atob(sections[1]);
    const user = JSON.parse(json);
    localStorage.setItem("jwt", body.jwt);
    return user;
}

export async function add(customer) {

    const init = {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(customer)
    }

    const response = await fetch(`${API_URL}/appuser/customer`, init);
    if (response.ok) {
        const body = await response.json();
        return body
    } else {
        return Promise.reject();
    }
}