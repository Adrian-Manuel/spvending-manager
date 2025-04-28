package com.SmartPadel.spvendingManagerApi.machine.infrastructure.persistence.adapters;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.persistance.entity.ClubEntity;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.persistance.repository.JpaClubRepository;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.utils.ClubHelperAdapter;
import com.SmartPadel.spvendingManagerApi.machine.domain.model.Machine;
import com.SmartPadel.spvendingManagerApi.machine.domain.ports.out.MachineRepositoryPort;
import com.SmartPadel.spvendingManagerApi.machine.infrastructure.persistence.entity.MachineEntity;
import com.SmartPadel.spvendingManagerApi.machine.infrastructure.persistence.repository.JpaMachineRepository;
import com.SmartPadel.spvendingManagerApi.machine.infrastructure.utils.MachineHelperAdapter;
import com.SmartPadel.spvendingManagerApi.machine.infrastructure.utils.MachineSpecification;
import com.SmartPadel.spvendingManagerApi.shared.Exceptions.ResourceNotFoundException;
import com.SmartPadel.spvendingManagerApi.shared.Utils.PersistenceUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;
@Transactional
@Component
@RequiredArgsConstructor
public class MachineRepositoryAdapter implements MachineRepositoryPort {
    private final JpaMachineRepository jpaMachineRepository;
    private final JpaClubRepository jpaClubRepository;
    @Override
    public Page<Machine> findAll(String search, Pageable pageable) {
        Specification<MachineEntity> spec = MachineSpecification.withFilter(search);
        return PersistenceUtils.mapPageOrThrow(jpaMachineRepository.findAll(spec,pageable), "No machines have been added yet", MachineEntity::toDomainModel);
    }
    @Override
    public Machine save(UUID clubId, Machine machine) {
        ClubEntity clubEntity= ClubHelperAdapter.getClubOrThrow(jpaClubRepository, clubId);
        machine.setClub(clubEntity);
        MachineEntity machineEntity=MachineEntity.fromDomainModel(machine);
        return jpaMachineRepository.save(machineEntity).toDomainModel();
    }
    @Override
    public Machine findById(UUID machineId) {
        return jpaMachineRepository.findById(machineId)
                .map(MachineEntity::toDomainModel)
                .orElseThrow(()->new ResourceNotFoundException("There is no machine with that id"));
    }
    @Override
    public Machine update(UUID clubId, UUID machineId, Machine machine) {
        MachineHelperAdapter.validateMachineExists(jpaMachineRepository, machineId);
        ClubEntity clubEntity=ClubHelperAdapter.getClubOrThrow(jpaClubRepository, clubId);
        machine.setMachineId(machineId);
        MachineEntity machineEntity=MachineEntity.fromDomainModel(machine);
        machineEntity.setClub(clubEntity);
        return jpaMachineRepository.save(machineEntity).toDomainModel();
    }
    @Override
    public void deleteById(UUID machineId) {
        MachineHelperAdapter.validateMachineExists(jpaMachineRepository, machineId);
        jpaMachineRepository.deleteById(machineId);
    }
    @Override
    public List<Machine> findAllMachinesByClubId(UUID clubId) {
        ClubHelperAdapter.validateClubExists(jpaClubRepository,clubId);
        Specification<MachineEntity> spec= MachineSpecification.belongsToClub(clubId);
        return PersistenceUtils.mapListOrThrow(jpaMachineRepository.findAll(spec), "No clubs found for this tenant", MachineEntity::toDomainModel);
    }
}
