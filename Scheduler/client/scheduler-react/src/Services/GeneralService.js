const GENERAL_API_URL = "http://localhost:8080/api/appuser";



export async function findByUserId(appUserId) {
    // console.log(`${localStorage.getItem("jwt")}`);
    const init = {
        
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("jwt")}`
        }
      }
    const response = await fetch(`${GENERAL_API_URL}/${appUserId}`, init);
    if (response.ok) {
      return response.json();
    } else {
      return Promise.reject();
    }
  }

    