import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import examenService from '../services/examen.service';
import './Listas.css';

const CorregirExamenesList: React.FC = () => {
  const [examenes, setExamenes] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [expandedAsignatura, setExpandedAsignatura] = useState<string | null>(null);
  const [filtroEstado, setFiltroEstado] = useState<string>('TODOS');
  const navigate = useNavigate();

  useEffect(() => {
    fetchExamenes();
  }, []);

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

  const handleCorregir = (examenId: number) => {
    examenService.corregirExamen(examenId).then(
      response => {
        alert(`Examen corregido. Nota: ${response.data.notaFinal}`);
        fetchExamenes();
      },
      error => {
        console.error(error);
        alert('Error al corregir examen');
      }
    );
  };

  const handleCorregirTodos = () => {
    examenService.corregirTodos().then(
      () => {
        alert('Todos los exámenes han sido corregidos');
        fetchExamenes();
      },
      error => {
        console.error(error);
        alert('Error al corregir exámenes');
      }
    );
  };

  if (loading) return <div>Cargando...</div>;

  const examenesPorAsignatura = examenes
    .filter(e => filtroEstado === 'TODOS' || e.estado === filtroEstado)
    .reduce((acc: any, curr: any) => {
      (acc[curr.asignatura] = acc[curr.asignatura] || []).push(curr);
      return acc;
    }, {});

  return (
    <div className="list-container">
      <h2>Gestión de Exámenes</h2>
      <div style={{marginBottom: '20px'}}>
        <button onClick={() => navigate('/dashboard')} className="btn-edit" style={{marginRight: '10px'}}>Volver al Panel</button>
        <button onClick={handleCorregirTodos} className="btn-edit" style={{marginRight: '10px'}}>Corregir Todos con IA</button>
        
        <select value={filtroEstado} onChange={(e) => setFiltroEstado(e.target.value)} className="btn-edit">
          <option value="TODOS">Todos</option>
          <option value="ASIGNADO">Pendientes (Asignados)</option>
          <option value="CORREGIDO">Corregidos</option>
        </select>
      </div>
      
      {Object.keys(examenesPorAsignatura).length === 0 ? (
        <p>No hay exámenes encontrados con el filtro seleccionado.</p>
      ) : (
        Object.keys(examenesPorAsignatura).map(asignatura => (
          <div key={asignatura} style={{marginBottom: '10px', border: '1px solid #ccc', padding: '10px'}}>
            <h3 
              onClick={() => setExpandedAsignatura(expandedAsignatura === asignatura ? null : asignatura)}
              style={{cursor: 'pointer', color: '#007bff'}}
            >
              {expandedAsignatura === asignatura ? '▼' : '▶'} Asignatura: {asignatura}
            </h3>
            
            {expandedAsignatura === asignatura && (
              <table>
                <thead>
                  <tr>
                    <th>Alumno</th>
                    <th>Grado</th>
                    <th>Tipo</th>
                    <th>Estado</th>
                    <th>Nota</th>
                    <th>Acciones</th>
                    <th>Detalle</th>
                  </tr>
                </thead>
                <tbody>
                  {examenesPorAsignatura[asignatura].map((e: any) => (
                    <tr key={e.id}>
                      <td>{e.alumno}</td>
                      <td>{e.grado}</td>
                      <td>{e.tipo}</td>
                      <td>{e.estado}</td>
                      <td>{e.estado === 'CORREGIDO' ? e.notaFinal : '-'}</td>
                      <td>
                        {e.estado === 'ASIGNADO' && (
                          <button onClick={() => handleCorregir(e.id)} className="btn-edit">Corregir</button>
                        )}
                      </td>
                      <td>
                        {e.estado === 'CORREGIDO' && (
                          <button onClick={() => navigate(`/examenes/detalle/${e.id}`)} className="btn-edit">Ver Detalle</button>
                        )}
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
