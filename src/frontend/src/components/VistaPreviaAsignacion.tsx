import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import examenService from '../services/examen.service';
import * as gradoService from '../services/grado.service';
import './Listas.css';

const VistaPreviaAsignacion: React.FC = () => {
  const [borradores, setBorradores] = useState<any[]>([]);
  const [grados, setGrados] = useState<any[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    Promise.all([
        examenService.getBorradores(),
        gradoService.getGrados()
    ]).then(([resBorradores, resGrados]) => {
        setBorradores(resBorradores.data);
        setGrados(resGrados.data);
    });
  }, []);

  const handleIrAAsignar = () => {
    navigate('/examenes/confirmar', { state: { asignaturaId } });
  };

  const asignaturaId = borradores.length > 0 ? borradores[0].asignaturaId : null;

  const handleVerDetalle = (borradorId: number) => {
    navigate(`/examenes/detalle-borrador/${borradorId}`, { state: { asignaturaId } });
  };

  return (
    <div className="list-container">
      <h2>Exámenes Generados</h2>
      <table>
        <thead>
          <tr>
            <th>Grado</th>
            <th>Tipo</th>
            <th>Num Preguntas</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {borradores.map((b: any, index: number) => (
            <tr key={index}>
              <td>{grados.find(g => g.id === b.gradoId)?.titulo || 'Desconocido'}</td>
              <td>{b.tipoExamen}</td>
              <td>{b.numPreguntas}</td>
              <td>
                <button className="btn-edit" onClick={() => handleVerDetalle(b.id)}>
                  Ver Detalle
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <div className="form-actions" style={{marginTop: '20px'}}>
        <button className="btn btn-primary" onClick={handleIrAAsignar}>Asignar Exámenes</button>
        <button className="btn btn-danger" onClick={() => navigate(`/asignaturas/editar/${asignaturaId}`)} style={{marginLeft: '10px'}}>Cancelar</button>
      </div>
    </div>
  );
};

export default VistaPreviaAsignacion;
