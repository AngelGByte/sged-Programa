/**
 * Extrae un mensaje amigable desde errores HTTP de Axios.
 */
export const getApiErrorMessage = (error, fallback = 'Ha ocurrido un error') => {
  const validationErrors = error?.response?.data?.validationErrors

  if (validationErrors && Object.keys(validationErrors).length > 0) {
    return Object.values(validationErrors).join(' | ')
  }

  return (
    error?.response?.data?.message ||
    error?.response?.data?.error ||
    fallback
  )
}
