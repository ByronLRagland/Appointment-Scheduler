const EMPLOYEE_API_URL = "http://localhost:8080/api/appuser";




export async function findByUserType() {

  const init = {
    method: "GET",
    headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${localStorage.getItem("jwt")}`
    }
  }
 
  const response = await fetch(`${EMPLOYEE_API_URL}/usertype/employee`, init);

  if (response.ok) {
    return response.json();
  } else {
    return Promise.reject();
  }

}

export async function findAllEmployees(activeStatus) {

  const init = {
    method: "GET",
    headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${localStorage.getItem("jwt")}`
    }
  }
    
  const response = await fetch(`${EMPLOYEE_API_URL}/users/employees/${activeStatus}`,init);
  if (response.ok) {
      return response.json();
  } else {
      return Promise.reject();
  }
}
