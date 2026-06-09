import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import { ArrowLeft } from 'lucide-react';
import { authHeader } from '../services/auth.service';
import './Listas.css';

const AlumnoExamenesList: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [examenes, setExamenes] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    if (id) {
      axios.get(`http://localhost:8080/api/examenes/alumno/${id}/corregidos`, { headers: authHeader() })
        .then(response => {
          setExamenes(response.data);
          setLoading(false);
        })
        .catch(() => {
          setError('Error al cargar los exámenes del alumno.');
          setLoading(false);
        });
    }
  }, [id]);

  if (loading) return <div className="loading-state">Cargando exámenes...</div>;
  if (error) return <div className="error-message">{error}</div>;

  return (
    <div className="list-container">
      <div className="list-header" style={{ display: 'flex', alignItems: 'center', gap: '1rem' }}>
        <button onClick={() => navigate(`/alumnos/editar/${id}`)} className="btn-icon">
          <ArrowLeft size={24} />
        </button>
        <h2>Exámenes Corregidos del Alumno</h2>
      </div>

      <div className="table-wrapper">
        {examenes.length > 0 ? (
          <table className="table">
            <thead>
              <tr>
                <th>Asignatura</th>
                <th>Tipo</th>
                <th>Nota</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {examenes.map((e: any) => (
                <tr key={e.id}>
                  <td>{e.asignatura}</td>
                  <td>{e.tipo}</td>
                  <td>{e.notaFinal}</td>
                  <td>
                    <button 
                      onClick={() => navigate(`/examenes/detalle/${e.id}`, { state: { from: `/alumnos/${id}/examenes` } })} 
                      className="btn-icon"
                    >
                      Ver Detalle
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        ) : (
          <p>No hay exámenes corregidos para este alumno.</p>
        )}
      </div>
    </div>
  );
};

export default AlumnoExamenesList;
