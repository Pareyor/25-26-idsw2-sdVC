import React, { useEffect, useState } from 'react';
import { getDocentes, deleteDocente } from '../services/docente.service';
import type { Docente } from '../services/docente.service';
import { Search, UserPlus, Edit, Trash2, ArrowLeft } from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import { normalizeString } from '../utils/searchUtils';
import './Listas.css';

const DocenteList: React.FC = () => {
  const [docentes, setDocentes] = useState<Docente[]>([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    fetchDocentes();
  }, []);

  const fetchDocentes = async () => {
    try {
      const response = await getDocentes();
      setDocentes(response.data);
      setLoading(false);
    } catch (err) {
      setError('Error al cargar los docentes');
      setLoading(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('¿Estás seguro de que deseas eliminar este docente? Esta acción no se puede deshacer.')) {
      try {
        await deleteDocente(id);
        setDocentes(docentes.filter(d => d.id !== id));
      } catch (err) {
        alert('Error al eliminar el docente');
      }
    }
  };

  const filteredDocentes = docentes.filter(docente => {
    const term = normalizeString(searchTerm);
    return normalizeString(docente.nombre).includes(term) ||
           normalizeString(docente.apellidos).includes(term) ||
           normalizeString(docente.username).includes(term);
  });

  if (loading) return <div className="loading-state">Cargando docentes...</div>;
  if (error) return <div className="error-message">{error}</div>;

  return (
    <div className="list-container">
      <div className="list-header">
        <div className="flex-row-center gap-4">
          <button 
            onClick={() => navigate('/dashboard')}
            className="btn-icon"
            title="Volver"
          >
            <ArrowLeft size={24} />
          </button>
          <h2>Gestión de Docentes</h2>
        </div>
        <button 
          onClick={() => navigate('/docentes/nuevo')}
          className="btn btn-primary"
        >
          <UserPlus size={20} />
          <span>Añadir Docente</span>
        </button>
      </div>

      <div className="search-bar">
        <div className="search-filter-wrapper">
          <Search size={20} />
          <input
            type="text"
            className="search-filter-input"
            placeholder="Buscar por nombre, apellidos o DNI..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>
      </div>

      <div className="table-wrapper">
        <table>
          <thead>
            <tr>
              <th>DNI/Username</th>
              <th>Nombre</th>
              <th>Apellidos</th>
              <th className="text-center">Acciones</th>
            </tr>
          </thead>
          <tbody>
            {filteredDocentes.map((docente) => (
              <tr key={docente.id}>
                <td>{docente.username}</td>
                <td>{docente.nombre}</td>
                <td>{docente.apellidos}</td>
                <td className="action-btns">
                  <button 
                    onClick={() => navigate(`/docentes/editar/${docente.id}`)}
                    className="btn-icon" 
                    title="Editar"
                  >
                    <Edit size={18} />
                  </button>
                  <button 
                    onClick={() => handleDelete(docente.id)}
                    className="btn-icon text-danger" 
                    title="Eliminar"
                  >
                    <Trash2 size={18} />
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default DocenteList;
