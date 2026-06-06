import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createDocente } from '../services/docente.service';
import { ArrowLeft, Save, User } from 'lucide-react';
import './Formularios.css';

const DocenteCreate: React.FC = () => {
  const [docente, setDocente] = useState({
    username: '',
    nombre: '',
    apellidos: '',
    email: '',
    password: '',
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setDocente(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      await createDocente(docente);
      navigate('/docentes');
    } catch (err: any) {
      setError(err.response?.data?.message || 'Error al crear el docente. Verifique si el DNI ya existe.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="form-container">
      <div className="form-header" style={{ display: 'flex', alignItems: 'center', gap: '1rem', marginBottom: '2rem' }}>
        <button 
          onClick={() => navigate('/docentes')}
          className="btn-icon"
        >
          <ArrowLeft size={24} />
        </button>
        <h1>Añadir Nuevo Docente</h1>
      </div>

      <form onSubmit={handleSubmit} className="standard-form">
        <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem', marginBottom: '1rem' }}>
          <User size={24} color="var(--primary)" />
          <h2 style={{ margin: 0, textAlign: 'left', fontSize: '1.5rem' }}>Datos del Docente</h2>
        </div>

        {error && (
          <div className="error-message" style={{ backgroundColor: 'rgba(244, 63, 94, 0.1)', color: 'var(--accent)', padding: '1rem', borderRadius: '8px', border: '1px solid var(--accent)', marginBottom: '1rem' }}>
            {error}
          </div>
        )}

        <div className="form-group">
          <label>DNI / Usuario</label>
          <input
            type="text"
            name="username"
            required
            value={docente.username}
            onChange={handleChange}
            placeholder="Ej: 12345678X"
          />
        </div>

        <div className="form-group">
          <label>Contraseña</label>
          <input
            type="password"
            name="password"
            required
            value={docente.password}
            onChange={handleChange}
            placeholder="********"
          />
        </div>

        <div className="form-group">
          <label>Email</label>
          <input
            type="email"
            name="email"
            required
            value={docente.email}
            onChange={handleChange}
            placeholder="ejemplo@correo.com"
          />
        </div>

        <div className="form-group">
          <label>Nombre</label>
          <input
            type="text"
            name="nombre"
            required
            value={docente.nombre}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label>Apellidos</label>
          <input
            type="text"
            name="apellidos"
            required
            value={docente.apellidos}
            onChange={handleChange}
          />
        </div>

        <div className="form-actions">
          <button
            type="submit"
            disabled={loading}
            className="btn btn-primary"
            style={{ 
              display: 'flex', 
              alignItems: 'center', 
              gap: '0.5rem', 
              padding: '0.75rem 1.5rem', 
              borderRadius: '12px', 
              color: 'white', 
              border: 'none', 
              cursor: 'pointer' 
            }}
          >
            <Save size={20} />
            <span>{loading ? 'Guardando...' : 'Guardar Docente'}</span>
          </button>
        </div>
      </form>
    </div>
  );
};

export default DocenteCreate;
