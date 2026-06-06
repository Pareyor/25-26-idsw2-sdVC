import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createGrado } from '../services/grado.service';
import { ArrowLeft, Save, GraduationCap } from 'lucide-react';
import './Formularios.css';

const GradoCreate: React.FC = () => {
  const [grado, setGrado] = useState({
    codigo: '',
    titulo: '',
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setGrado(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      await createGrado(grado);
      navigate('/grados');
    } catch (err: any) {
      setError(err.response?.data?.message || 'Error al crear el grado. Verifique si el código ya existe.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="form-container">
      <div className="form-header-actions">
        <button 
          onClick={() => navigate('/grados')}
          className="btn-icon"
          title="Volver"
        >
          <ArrowLeft size={24} />
        </button>
      </div>

      <h2>Añadir Nuevo Grado</h2>

      <form onSubmit={handleSubmit} className="standard-form">
        {error && (
          <div className="error-message">
            {error}
          </div>
        )}

        <div className="form-group">
          <label>Código del Grado</label>
          <input
            type="text"
            name="codigo"
            required
            value={grado.codigo}
            onChange={handleChange}
            placeholder="Ej: GII, GADE..."
          />
        </div>

        <div className="form-group">
          <label>Título del Grado</label>
          <input
            type="text"
            name="titulo"
            required
            value={grado.titulo}
            onChange={handleChange}
            placeholder="Ej: Grado en Ingeniería Informática"
          />
        </div>

        <div className="form-actions">
          <button
            type="submit"
            disabled={loading}
            className="btn btn-primary"
          >
            <Save size={20} />
            <span>{loading ? 'Guardando...' : 'Guardar Grado'}</span>
          </button>
        </div>
      </form>
    </div>
  );
};

export default GradoCreate;
