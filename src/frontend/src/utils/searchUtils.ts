/**
 * Normaliza una cadena de texto eliminando tildes y convirtiéndola a minúsculas
 * para facilitar búsquedas insensibles a acentos y mayúsculas.
 */
export const normalizeString = (str: string): string => {
  return str
    .toLowerCase()
    .normalize("NFD")
    .replace(/[\u0300-\u036f]/g, "");
};
