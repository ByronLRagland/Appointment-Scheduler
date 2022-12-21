const SERVICES_API_URL = "http://localhost:8080/api/services";

export async function findAll(activeStatus) {
    
    const response = await fetch(`${SERVICES_API_URL}/find/${activeStatus}`);
    if (response.ok) {
        return response.json();
    } else {
        return Promise.reject();
    }
}

export async function findById(serviceId) {
    const response = await fetch(`${SERVICES_API_URL}/${serviceId}`);
    if (response.ok) {
        return response.json();
    } else {
        return Promise.reject();
    }
}

export async function add(service) {

    const init = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("jwt")}`
        },
        body: JSON.stringify(service)
    };

    const response = await fetch(SERVICES_API_URL, init);
    if (response.ok) {
        return Promise.resolve();
    } else if (response.status === 400) {
        const errs = await response.json();
        return Promise.reject(errs);
    } else {
        return Promise.reject();
    }
}

async function update(service) {

    const init = {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("jwt")}`
        },
        body: JSON.stringify(service)
    };

    const response = await fetch(`${SERVICES_API_URL}/${service.serviceId}`, init);
    if (response.ok) {
        return Promise.resolve();
    } else if (response.status === 400) {
        const errs = await response.json();
        return Promise.reject(errs);
    } else {
        return Promise.reject();
    }
}

export async function save(service) {
    return service.serviceId > 0 ? update(service) : add(service);
}

export async function deleteById(serviceId) {
    const init = {
        method: "DELETE",
        headers: {
            "Authorization": `Bearer ${localStorage.getItem("jwt")}`
        }
    };
    const response = await fetch(`${SERVICES_API_URL}/${serviceId}`, init);
    if (response.ok) {
        return Promise.resolve();
    } else {
        return Promise.reject();
    }
}