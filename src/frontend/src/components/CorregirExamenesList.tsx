import React, { useState, useEffect } from 'react';
import examenService from '../services/examen.service';
import './Listas.css';

const CorregirExamenesList: React.FC = () => {
  const [examenes, setExamenes] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

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

  if (loading) return <div>Cargando...</div>;

  return (
    <div className="list-container">
      <h2>Exámenes para Corregir</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Alumno</th>
            <th>Asignatura</th>
            <th>Tipo</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {examenes.map(e => (
            <tr key={e.id}>
              <td>{e.id}</td>
              <td>{e.alumno}</td>
              <td>{e.asignatura}</td>
              <td>{e.tipo}</td>
              <td>
                <button onClick={() => handleCorregir(e.id)}>Corregir</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default CorregirExamenesList;
