const APPOINTMENTS_API_URL = "http://localhost:8080/api/appointment";

export async function findAll() {
  const response = await fetch(APPOINTMENTS_API_URL);
  if (response.ok) {
    return response.json();
  } else {
    return Promise.reject();
  }
}

export async function findById(appointmentId) {

  const init = {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${localStorage.getItem("jwt")}` 
      
    }
  }

  const response = await fetch(`${APPOINTMENTS_API_URL}/${appointmentId}`, init);
  if (response.ok) {
    return response.json();
  } else {
    return Promise.reject();
  }
}

export async function findByEmployee(userId) {

  const init = {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${localStorage.getItem("jwt")}`
      
    }
  }

  const response = await fetch(
    `${APPOINTMENTS_API_URL}/find/${userId}`, init
  );
  if (response.ok) {
    return response.json();
  } else {
    return Promise.reject();
  }
}

export async function findByCustomer(userId) {

  const init = {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${localStorage.getItem("jwt")}`
      
    }
  }

  const response = await fetch(
    `${APPOINTMENTS_API_URL}/find/customer/${userId}`, init
  );
  if (response.ok) {
    return response.json();
  } else {
    return Promise.reject();
  }
}

export async function add(appointment) {
  const init = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("jwt")}`,
    },
    body: JSON.stringify(appointment),
  };

  const response = await fetch(APPOINTMENTS_API_URL, init);
  if (response.ok) {
    return Promise.resolve();
  } else if (response.status === 400) {
    const errs = await response.json();
    return Promise.reject(errs);
  } else {
    return Promise.reject();
  }
}

async function update(appointment) {
  const init = {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("jwt")}`,
    },
    body: JSON.stringify(appointment),
  };

  const response = await fetch(
    `${APPOINTMENTS_API_URL}/${appointment.appointmentId}`,
    init
  );
  if (response.ok) {
    return Promise.resolve();
  } else if (response.status === 400) {
    const errs = await response.json();
    return Promise.reject(errs);
  } else {
    return Promise.reject();
  }
}

export async function save(appointment) {
  return appointment.appointmentId > 0 ? update(appointment) : add(appointment);
}

export async function deleteById(appointmentId) {
  const init = {
    method: "DELETE",
    headers: {
      Authorization: `Bearer ${localStorage.getItem("jwt")}`,
    },
  };
  const response = await fetch(
    `${APPOINTMENTS_API_URL}/${appointmentId}`,
    init
  );
  if (response.ok) {
    return Promise.resolve();
  } else {
    return Promise.reject();
  }
}

export async function cancelById(appointmentId) {
  const init = {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("jwt")}`,
    },
  };
  const response = await fetch(
    `${APPOINTMENTS_API_URL}/cancel/${appointmentId}`,
    init
  );
  if (response.ok) {
    return Promise.resolve();
  } else if (response.status === 400) {
    const errs = await response.json();
    return Promise.reject(errs);
  } else {
    return Promise.reject();
  }
}
