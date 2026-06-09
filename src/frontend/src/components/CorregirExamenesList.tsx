import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import examenService from '../services/examen.service';
import './Listas.css';

const CorregirExamenesList: React.FC = () => {
  const [examenes, setExamenes] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [expandedAsignatura, setExpandedAsignatura] = useState<string | null>(null);
  const [filtroEstado, setFiltroEstado] = useState<string>('TODOS');
  const navigate = useNavigate();
  const location = useLocation();
  const state = location.state as { asignaturaNombre?: string, asignaturaId?: string };
  const initialAsignatura = state?.asignaturaNombre;

  useEffect(() => {
    console.log("DEBUG - CorregirExamenesList mounted. Location state:", location.state);
    if (!initialAsignatura) {
      console.log("DEBUG - Redirigiendo al dashboard por falta de asignatura");
      navigate('/dashboard');
    } else {
      fetchExamenes();
    }
  }, []);

  useEffect(() => {
    if (initialAsignatura && !loading) {
      setExpandedAsignatura(initialAsignatura);
    }
  }, [initialAsignatura, loading]);

  const fetchExamenes = () => {
    examenService.getExamenesParaCorregir().then(
      response => {
        setExamenes(response.data);
        setLoading(false);
      },
      error => {
        console.error(error);
        setLoading(false);
      }
    );
  };

  const handleCorregirTodos = () => {
    const action = initialAsignatura 
      ? examenService.corregirPorAsignatura(parseInt(location.state.asignaturaId)) 
      : examenService.corregirTodos();

    action.then(
      () => {
        alert('Los exámenes han sido corregidos');
        fetchExamenes();
      },
      error => {
        console.error(error);
        alert('Error al corregir exámenes');
      }
    );
  };

  const handleVolver = () => {
    if (state?.asignaturaId) {
      navigate(`/asignaturas/editar/${state.asignaturaId}`);
    } else {
      navigate('/dashboard');
    }
  };

  if (loading) return <div>Cargando...</div>;

  const examenesPorTipo = examenes
    .filter(e => {
        const estadoMatches = filtroEstado === 'TODOS' || e.estado === filtroEstado;
        const asignaturaMatches = !initialAsignatura || e.asignatura === initialAsignatura;
        return estadoMatches && asignaturaMatches;
    })
    .reduce((acc: any, curr: any) => {
      (acc[curr.tipo] = acc[curr.tipo] || []).push(curr);
      return acc;
    }, {});

  return (
    <div className="list-container">
      <h2>{initialAsignatura ? `Corregir Exámenes: ${initialAsignatura}` : 'Gestión de Exámenes'}</h2>
      <div style={{marginBottom: '20px'}}>
        <button onClick={handleVolver} className="btn-edit" style={{marginRight: '10px'}}>Volver</button>
        <button onClick={handleCorregirTodos} className="btn-edit" style={{marginRight: '10px'}}>
            {initialAsignatura ? 'Corregir Asignatura con IA' : 'Corregir Todos con IA'}
        </button>
        
        <select value={filtroEstado} onChange={(e) => setFiltroEstado(e.target.value)} className="btn-edit">
          <option value="TODOS">Todos</option>
          <option value="ASIGNADO">Pendientes (Asignados)</option>
          <option value="CORREGIDO">Corregidos</option>
        </select>
      </div>
      
      {Object.keys(examenesPorTipo).length === 0 ? (
        <p>No hay exámenes encontrados con el filtro seleccionado.</p>
      ) : (
        Object.keys(examenesPorTipo).map(tipo => (
          <div key={tipo} style={{marginBottom: '10px', border: '1px solid #ccc', padding: '10px'}}>
            <h3 
              onClick={() => setExpandedAsignatura(expandedAsignatura === tipo ? null : tipo)}
              style={{cursor: 'pointer', color: '#007bff'}}
            >
              {expandedAsignatura === tipo ? '▼' : '▶'} Tipo de Examen: {tipo}
            </h3>
            
            {expandedAsignatura === tipo && (
              <table>
                <thead>
                  <tr>
                    <th>Alumno</th>
                    <th>Grado</th>
                    <th>Asignatura</th>
                    <th>Estado</th>
                    <th>Nota</th>
                    <th>Acciones</th>
                  </tr>
                </thead>
                <tbody>
                  {examenesPorTipo[tipo].map((e: any) => (
                    <tr key={e.id}>
                      <td>{e.alumno}</td>
                      <td>{e.grado}</td>
                      <td>{e.asignatura}</td>
                      <td>{e.estado}</td>
                      <td>{e.estado === 'CORREGIDO' ? e.notaFinal : '-'}</td>
                      <td>
                        <div style={{ display: 'flex', gap: '5px' }}>
                          <button 
                            onClick={() => navigate(`/examenes/detalle/${e.id}`, { state: location.state })} 
                            className="btn-edit"
                          >
                            {e.estado === 'CORREGIDO' ? 'Ver Detalle' : 'Ver Examen'}
                          </button>
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            )}
          </div>
        ))
      )}
    </div>
  );
};

export default CorregirExamenesList;
