export const hasNumber= (str) => {
	return (/\d/.test(str));
}

export const hasUpper = (str) => {
	return (/[a-z]/.test(str));
}

export const hasLower = (str) => {
	return (/[A-Z]/.test(str));
}

export const validatePassword = (password) => {
	return (hasNumber(password) && hasUpper(password) && hasLower(password));
}

export const validateEmail = (email) => {
	return (email.indexOf('@') !== -1);
}
