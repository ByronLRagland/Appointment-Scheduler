const CUSTOMER_API_URL = "http://localhost:8080/api/appuser";


export async function findAllCustomers(activeStatus) {

    const init = {
      method: "GET",
      headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${localStorage.getItem("jwt")}`
      }
    }
      
    const response = await fetch(`${CUSTOMER_API_URL}/users/customers/${activeStatus}`,init);
    if (response.ok) {
        return response.json();
    } else {
        return Promise.reject();
    }
  }